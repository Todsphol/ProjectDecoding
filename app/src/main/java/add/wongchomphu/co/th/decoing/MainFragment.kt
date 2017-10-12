package add.wongchomphu.co.th.decoing


import android.R.attr.*
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import android.widget.CheckBox
import android.text.InputFilter
import android.widget.EditText

class MainFragment : Fragment(){

    @BindView(R.id.tv_show_input) lateinit var tvShowEncrypt: TextView
    @BindView(R.id.tv_show_output) lateinit var tvShowOutput: TextView
    @BindView(R.id.tv_show_k) lateinit var tvShowK: TextView
    @BindView(R.id.edt_encode) lateinit var edtEncode: EditText
    @BindView(R.id.edt_input_k) lateinit var edtValueK: EditText
    @BindView(R.id.btn_encrypt) lateinit var btnEncode: Button
    @BindView(R.id.btn_decrypt) lateinit var btnDecode: Button
    @BindView(R.id.cb_ShiftCaesar) lateinit var cbSiftCaesar: CheckBox
    @BindView(R.id.cb_rail_fence_cipher) lateinit var cbRailFenceCipher: CheckBox
    @BindView(R.id.cb_OTP) lateinit var cbOTP: CheckBox

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_mian, container, false)
        ButterKnife.bind(this, view)
        selectCheckbox()
        return view
    }

    private val indexOfChar = 26
    private fun freeValue() {
        Toast.makeText(context, getString(R.string.Error_Message), Toast.LENGTH_LONG).show()
    }

    private fun decryptShiftCaeSar() {
        val key = edtValueK.text.toString()
        val encoded = shiftCaesarEncrypt(edtEncode.text.toString(), key.toInt())
        textViewShow(encoded, key)
    }

    private fun encryptShiftCaeSar() {
        val key = edtValueK.text.toString()
        val encodedElse = shiftCaesarEncryptElse(edtEncode.text.toString(), key.toInt())
        val encoded = shiftCaesarDecrypt(encodedElse, key.toInt())
        textViewShow(encoded, key)
    }

    private fun decryptOTP() {
        val key = edtValueK.text.toString()
        val encoded = Onetimepadencryptcipher().Decryption(edtEncode.text.toString(), key)
        textViewShow(encoded, key)
    }

    private fun decryptRailFenceCipher() {
        val key = edtValueK.text.toString()
        val encoded = railFenceCipherDecrypt(edtEncode.text.toString(), key.toInt())
        textViewShow(encoded, key)
    }

    private fun encryptRailFenceCipher() {
        val key = edtValueK.text.toString()
        val encoded = railFenceCipherEncrypt(edtEncode.text.toString(), key.toInt())
        textViewShow(encoded, key)
    }
    private fun encryptOTP() {
        val key = edtValueK.text.toString()
        val encoded = Onetimepadencryptcipher().Encryption(edtEncode.text.toString(), key)
        textViewShow(encoded, key)
    }
    private fun textViewShow(encoded: String, key: String) {
        tvShowEncrypt.text = edtEncode.text.toString()
        tvShowOutput.text = encoded
        tvShowK.text = key
    }
    private fun selectCheckbox() {
        cbSiftCaesar.setOnClickListener {
            checkBoxShiftTrue()
            clearEditText()
            shiftCaesar()
        }
        cbRailFenceCipher.setOnClickListener {
            checkBoxRailFenceTrue()
            clearEditText()
            railFenceCipher()
        }
        cbOTP.setOnClickListener {
            checkBoxOTPTrue()
            clearEditText()
            oneTimePad()
        }
    }

    private fun clearEditText() {
        edtEncode.setText("")
        edtValueK.setText("")
    }

    private fun checkBoxShiftTrue() {
        cbRailFenceCipher.isChecked = false
        cbOTP.isChecked = false
    }

    private fun checkBoxRailFenceTrue() {
        cbSiftCaesar.isChecked = false
        cbOTP.isChecked = false
    }

    private fun checkBoxOTPTrue() {
        cbSiftCaesar.isChecked = false
        cbRailFenceCipher.isChecked = false
    }

    private fun setEditTextMaxLength(editText: EditText, length: Int) {
        val FilterArray = arrayOfNulls<InputFilter>(1)
        FilterArray[0] = InputFilter.LengthFilter(length)
        editText.filters = FilterArray
    }

    private fun shiftCaesar() {
        edtValueK.inputType = numberPickerStyle
        btnEncode.setOnClickListener {
            if (edtEncode.length() == 0 || edtValueK.length() == 0) {
                freeValue()
            } else {
                encryptShiftCaeSar()
            }
        }
        btnDecode.setOnClickListener {
            if (edtEncode.length() == 0 || edtValueK.length() == 0) {
                freeValue()
            } else {
                decryptShiftCaeSar()
            }
        }
    }

    private fun railFenceCipher() {
        edtValueK.inputType = numberPickerStyle
        btnEncode.setOnClickListener {
            if (edtEncode.length() == 0 || edtValueK.length() == 0) {
                freeValue()
            } else {
                encryptRailFenceCipher()
            }
        }
        btnDecode.setOnClickListener {
            if (edtEncode.length() == 0 || edtValueK.length() == 0) {
                freeValue()
            } else {
                decryptRailFenceCipher()
            }
        }
    }

    private fun oneTimePad() {
        edtValueK.inputType = text
        setEditTextMaxLength(edtValueK,10)
        setEditTextMaxLength(edtEncode,10)
        btnEncode.setOnClickListener {
            if (edtEncode.length() == 0 || edtValueK.length() == 0) {
                freeValue()
            } else {
                encryptOTP()
            }
        }
        btnDecode.setOnClickListener {
            if (edtEncode.length() == 0 || edtValueK.length() == 0) {
                freeValue()
            } else {
                decryptOTP()
            }
        }
    }


    private fun shiftCaesarEncrypt(input: String, key: Int): String {
        val offset = key % indexOfChar
        if (offset == 0) return input
        var output: Char
        val chars = CharArray(input.length)
        for ((index, position) in input.withIndex()) {
            if (position in 'A'..'Z') {
                output = position + offset
                if (output > 'Z') output -= indexOfChar
            } else if (position in 'a'..'z') {
                output = position + offset
                if (output > 'z') output -= indexOfChar
            } else
                output = position
            chars[index] = output
        }
        return chars.joinToString("")
    }

    private fun shiftCaesarEncryptElse(input: String, key: Int): String {
        val offset = (key % indexOfChar) - key
        if (offset == 0) return input
        var output: Char
        val chars = CharArray(input.length)
        for ((index, position) in input.withIndex()) {
            if (position in 'A'..'Z') {
                output = position + offset
                if (output > 'Z') output += indexOfChar
            } else if (position in 'a'..'z') {
                output = position + offset
                if (output > 'z') output += indexOfChar
            } else
                output = position
            chars[index] = output
        }
        return chars.joinToString("")
    }

    private fun shiftCaesarDecrypt(input: String, key: Int): String = shiftCaesarEncrypt(input, indexOfChar - key)
    private fun railFenceCipherEncrypt(text: String, key: Int): String {
        if (key < 2 || key >= text.length) return text
        val sb = StringBuilder()
        var step1: Int
        var step2: Int
        for (row in 0 until key) {
            if (row == 0 || row == key - 1) {
                step2 = (key - 1) * 2
                step1 = step2
            } else {
                step1 = (key - 1) * 2 - row * 2
                step2 = row * 2
            }
            var x = 0
            var y = row
            while (y < text.length) {
                y += if (x == 0) {
                    sb.append(text[row])
                    step1
                } else {
                    if (x % 2 != 0) {
                        sb.append(text[y])
                        step2
                    } else {
                        sb.append(text[y])
                        step1
                    }
                }
                x++
            }
        }
        return sb.toString()
    }

    private fun railFenceCipherDecrypt(text: String, key: Int): String {

        val boundaries: Int

        val ikey = key - 2

        val rowLengths = IntArray(key)

        if (text.length % (key - 1) != 0) {
            boundaries = text.length / (key - 1) + 1
        } else
            boundaries = text.length / (key - 1)

        val minRowLen = boundaries - 1

        for (i in rowLengths.indices) {
            rowLengths[i] = minRowLen
        }
        val remainder = text.length - (boundaries + ikey * minRowLen)

        if (boundaries % 2 == 0) {
            rowLengths[0] = boundaries / 2
            rowLengths[key - 1] = boundaries / 2
            for (i in key - 2 downTo key - 2 - remainder + 1) {
                rowLengths[i]++
            }
        } else {
            rowLengths[0] = boundaries / 2 + 1
            rowLengths[key - 1] = boundaries / 2
            for (i in 1..remainder) {
                rowLengths[i]++
            }
        }

        val steps = IntArray(key - 1)
        steps[0] = rowLengths[0]
        for (i in 1 until key - 1) {
            steps[i] = rowLengths[i] + steps[i - 1]
        }

        val sb = StringBuilder()

        var lastBackward = 1

        var backwardCounter = steps.size - 2

        var frw = true

        var x = 0
        var direction = 0
        while (x < text.length - 1) {

            if (x == 0) {
                sb.append(text[0])
            }
            if (direction >= key - 1) {
                direction = 0
                if (frw) {
                    frw = false
                    steps[steps.size - 1]++
                } else {
                    frw = true
                    lastBackward++
                    backwardCounter = steps.size - 2
                }
                for (i in 0 until steps.size - 1) {
                    steps[i]++
                }
            }
            if (frw) {
                if (direction == key - 2) {
                    sb.append(text[steps[direction]])
                } else {
                    sb.append(text[steps[direction]])
                }
            } else {
                if (direction == key - 2) {
                    sb.append(text[lastBackward])
                } else {
                    sb.append(text[steps[backwardCounter]])
                }
                backwardCounter--
            }
            x++
            direction++
        }
        return sb.toString()
    }

    class otpp {
        var a = 97
        var all = CharArray(27)

        init {
            for (i in 0..25) {
                all[i] = a.toChar()
                a++
            }
        }

        fun Ipos(c: Char): Int {
            var i = 0
            while (i < 26) {
                if (all[i] == c) {
                    break
                }
                i++
            }
            return i
        }

        fun Cpos(c: Int): Char {
            var i = 0
            while (i < c) {
                i++

            }
            return all[i]
        }
    }



    companion object {
        fun newInstance(): MainFragment {
            val bundle = Bundle()
            val fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
