package com.dicoding.picodiploma.storyapp.view.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dicoding.picodiploma.storyapp.R

class PasswordValidationView : AppCompatEditText, OnTouchListener {

//    private lateinit var clearButtonImage: Drawable
    private var isValidPassword: Boolean = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Menambahkan hint pada editText
//        hint = "Masukkan nama Anda"

        // Menambahkan text aligmnet pada editText
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        // Menginisialisasi gambar clear button
//        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_image) as Drawable

        // Menambahkan aksi kepada clear button
        setOnTouchListener(this)

        // Menambahkan aksi ketika ada perubahan text akan memunculkan clear button
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
            }

            override fun afterTextChanged(editable: Editable) {
                // Do nothing.
                // Check the length of the entered password
            if (editable.length < 8) {
                // Set an error message
                error = "Password minimal harus memiliki 8 karakter"
                isValidPassword = false
            } else {
                // Clear any previous error
                error = null
                isValidPassword = true
            }
            }

        })
    }

    //mengecek apakah password sudah valid
    fun getValidatedPassword(): Boolean? {
        return isValidPassword
    }


    // Menampilkan clear button
    private fun showClearButton() {
//        setButtonDrawables(endOfTheText = clearButtonImage)
    }

    // Menghilangkan clear button
    private fun hideClearButton() {
        setButtonDrawables()
    }

    //Mengkonfigurasi button
    private fun setButtonDrawables(startOfTheText: Drawable? = null, topOfTheText:Drawable? = null, endOfTheText:Drawable? = null, bottomOfTheText: Drawable? = null){
        // Sets the Drawables (if any) to appear to the left of,
        // above, to the right of, and below the text.
        setCompoundDrawablesWithIntrinsicBounds(startOfTheText, topOfTheText, endOfTheText, bottomOfTheText)
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        return false
    }
}




