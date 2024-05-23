package com.dicoding.picodiploma.storyapp.view.story

import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.dicoding.picodiploma.storyapp.R
import com.dicoding.picodiploma.storyapp.databinding.ActivityAddStoryBinding
import android.Manifest
import android.content.Intent
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.dicoding.picodiploma.storyapp.data.GlobalVariables
import com.dicoding.picodiploma.storyapp.data.response.PostStoryResponse
import com.dicoding.picodiploma.storyapp.data.retrofit.ApiConfig
import com.dicoding.picodiploma.mycamera.getImageUri
import com.dicoding.picodiploma.mycamera.uriToFile
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding

    private var currentImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.gallery.setOnClickListener { startGallery() }
        binding.camera.setOnClickListener {
            startCamera()
        }
        binding.upload.setOnClickListener { uploadImage() }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            showToast("No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            // Log.d("Image URI", "showImage: $it")
            binding.imageView.setImageURI(it)
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this)
            val description = binding.descriptionEditText.text.toString()

            showLoading(true)

            val requestBody = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )
            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getApiService()
                    val successResponse = apiService.postStory(GlobalVariables.token, multipartBody, requestBody)
                    if (successResponse != null) {
                        showToast(successResponse.message)
                        showLoading(false)

                        val intent = Intent(this@AddStoryActivity, StoryActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, PostStoryResponse::class.java)
                    if (errorResponse != null) {
                        showToast(errorResponse.message)
                        showLoading(false)
                    }
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}