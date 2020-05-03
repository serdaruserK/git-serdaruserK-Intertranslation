package com.example.intertranslation


import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import kotlinx.android.synthetic.main.activity_main2.*




class Main2Activity : AppCompatActivity() {
    private var aranankelime = -1
    private var sonuckelime = -1
    private var areModelsDownloaded = false
    private var currentTranslator: FirebaseTranslator? = null
    private var stringPassed = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setUpPickers()

        if (savedInstanceState == null) {
            val extras = intent.extras
            if (extras == null) {
                stringPassed = ""
            } else {
                stringPassed = extras.getString("textToTranslate").toString()
            }
        } else {
            stringPassed = savedInstanceState.getSerializable("textToTranslate") as String
        }
        arananKelime.setText(stringPassed)
    }

    fun cık(view: View) {

        finish()
    }

    fun ceviri(view: View) {

        if (arananKelime.text.isBlank()) {
            showAlert("Kelime Giriniz..!!")
            return
        }
        if (aranankelime == -1 || sonuckelime == -1) {
            showAlert("Dilleri Seçiniz..!!")
            return
        }

        currentTranslator = prepareATranslatorWith(aranankelime, sonuckelime)

    }

    private fun setUpPickers() {
        val diller = arrayOf("Türkçe", "İngilizce", "Almanca", "Fransızca")
        var fromPicker = cevirilendil
        fromPicker.minValue = 0
        fromPicker.maxValue = diller.size - 1
        fromPicker.displayedValues = diller
        fromPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        fromPicker.setOnValueChangedListener { picker, oldvalue, newvalue ->

            aranankelime = getFirebaseLanguageFrom(diller[newvalue])
        }

        var toPicker = cevirilecekdil
        toPicker.minValue = 0
        toPicker.maxValue = diller.size - 1
        toPicker.displayedValues = diller
        toPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        toPicker.setOnValueChangedListener { picker, oldvalue, newvalue ->

            sonuckelime = getFirebaseLanguageFrom(diller[newvalue])
        }
    }


    private fun prepareATranslatorWith(sourceLang: Int, destinationLang: Int): FirebaseTranslator {

        val options = FirebaseTranslatorOptions.Builder()
            .setSourceLanguage(sourceLang)
            .setTargetLanguage(destinationLang)
            .build()
        val translator = FirebaseNaturalLanguage.getInstance().getTranslator(options)
        translator.downloadModelIfNeeded()
            .addOnSuccessListener {
                areModelsDownloaded = true
                runOnUiThread {

                    translateTextNow()
                }
            }
            .addOnFailureListener {
                showAlert("HATA!TEKRAR DENEYİN..!!")
                areModelsDownloaded = false
            }
        return translator
    }

    private fun getFirebaseLanguageFrom(userSelection: String): Int {

        when (userSelection) {
            "Türkçe" -> return FirebaseTranslateLanguage.TR
            "İngilizce" -> return FirebaseTranslateLanguage.EN
            "Almanca" -> return FirebaseTranslateLanguage.DE
            "Fransızca" -> return FirebaseTranslateLanguage.FR
        }
        return -1

    }

    private fun showAlert(message: String) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("HATA!!")
        dialog.setMessage(message)
        dialog.setPositiveButton(" OK!! ",
            { dialog, id -> dialog.dismiss() })
        dialog.show()

    }

    private fun translateTextNow() {
        currentTranslator!!.translate(arananKelime.text.toString())
            .addOnSuccessListener { translatedText ->
                runOnUiThread {
                    sonucKel.text = translatedText
                }
            }
            .addOnFailureListener {
                runOnUiThread {
                    showAlert("ÇEVİRİLEMEDİ..!!")
                }
            }
    }
}

















