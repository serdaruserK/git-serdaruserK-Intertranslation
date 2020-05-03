package com.example.intertranslation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity : AppCompatActivity() {

    var k = "."
    var s = "."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login.setOnClickListener(){

            var ka = editText.text.toString()
            var sfr = editText2.text.toString()

            if(ka==k && sfr==s){
                Toast.makeText(this,"Giriş Başarılı", Toast.LENGTH_SHORT).show()
                var ileri = Intent(this,Main2Activity::class.java)
                startActivity(ileri)
            }else if(ka==k && sfr!=s){
                if(sfr==""){
                    var mesaj = "şifre boş bırakılamaz"
                    textView3.text = mesaj
                }else{
                    var mesaj = "şifre yanlış"
                    textView3.text = mesaj
                }

            }else if(ka!=k && sfr==s){
                if(ka==""){
                    var mesaj = "kullanıcı adı boş bırakılamaz"
                    textView3.text = mesaj
                }else{
                    var mesaj = "kullanıcı adı yanlış"
                    textView3.text = mesaj
                }
            } else if(ka!=k && sfr!=s){
                if(ka=="" && sfr==""){
                    var mesaj = "kullanıcı adı ve şifre boş geçilemez"
                    textView3.text = mesaj
                }else if(ka!=k && sfr=="") {
                    var mesaj = "kullanıcı adı yanlış ve şifre boş"
                    textView3.text = mesaj
                } else if(ka=="" && sfr!=s) {
                    var mesaj = "şifre yanlış ve kullanıcı adı boş"
                    textView3.text = mesaj
                }else {
                    var mesaj = "şifre yanlış ve kullanıcı adı yanlış"
                    textView3.text = mesaj
                }



            }






        }
    }
}