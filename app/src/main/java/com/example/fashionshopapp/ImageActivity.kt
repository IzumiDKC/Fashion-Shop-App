package com.example.fashionshopapp

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

class ImageActivity: ComponentActivity() {

    // Bat dau them
    private lateinit var selectedImageView: ImageView
    private lateinit var confirmButton: Button
    private var detectedLabels: ArrayList<String>? = null
    // Ket thuc them

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            displaySelectedImage(uri)

            processImageUri(uri)
        } else {
            Toast.makeText(this, "Không chọn được ảnh!", Toast.LENGTH_SHORT).show()

            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun displaySelectedImage(uri: Uri){
        selectedImageView.setImageURI(uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        // Ánh xạ các View
        selectedImageView = findViewById(R.id.selectedImage)

        // Yêu cầu chọn ảnh khi activity được khởi chạy
        imagePickerLauncher.launch("image/*")

    }

    private fun processImageUri(uri: Uri) {
        try {
            val image = InputImage.fromFilePath(this, uri)
            val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
            labeler.process(image)
                .addOnSuccessListener { labels ->
                    Toast.makeText(this@ImageActivity, "Đang phân tích. Vui lòng chờ trong giây lát.", Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch{
                        delay(2000)
                        detectedLabels = ArrayList(labels.map { it.text })
                        Log.d("ImageActivity", "Labels detected: $detectedLabels")


                        //Toast.makeText(this@ImageActivity, "Đang phân tích. Vui lòng chờ trong giây lát.", Toast.LENGTH_SHORT).show()

                        val resultIntent = Intent().apply {
                            putStringArrayListExtra("detectedLabels", detectedLabels)
                        }
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    }

                }
                .addOnFailureListener { e ->
                    Log.e("ImageActivity", "Error processing image", e)
                    //Moi nua ne
                    Toast.makeText(this, "Lỗi phân tích ảnh!", Toast.LENGTH_SHORT).show()

                    setResult(Activity.RESULT_CANCELED)
                    finish()
                }
        } catch (e: IOException) {
            Log.e("ImageActivity", "Lỗi đọc ảnh: ", e)
            Toast.makeText(this, "Lỗi xử lý ảnh!", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}