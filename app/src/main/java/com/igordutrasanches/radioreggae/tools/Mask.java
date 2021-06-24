package com.igordutrasanches.radioreggae.tools;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.text.NumberFormat;
import java.util.Locale;

public abstract class Mask {


    public static String formatarTelefoneCelular(String str) {
        char[] charArray;
        String unmask = unmask(str);
        String str2 = "";
        int i = 0;
        for (char c : (unmask.length() > 10 ? "### (##) # ####-####" : "###  (##) ####-####").toCharArray()) {
            if (c == '#' || unmask.length() <= 0) {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append(unmask.charAt(i));
                    str2 = sb.toString();
                    i++;
                } catch (Exception unused) {
                }
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append(c);
                str2 = sb2.toString();
            }
        }
        return str2;
    }

    public static TextWatcher insertTelefoneCelular(final EditText editText) {
        return new TextWatcher() {
            boolean isUpdating;
            String mask;
            String old;

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            {
                String str = "";
                this.mask = str;
                this.old = str;
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                char[] charArray;
                String unmask = Mask.unmask(charSequence.toString());
                if (unmask.length() <= 10) {
                    this.mask = "##(##)####-####";
                } else {
                    this.mask = "##-(##)#-####-####";
                }
                if (this.isUpdating) {
                    this.old = unmask;
                    this.isUpdating = false;
                    return;
                }
                String str = "";
                int i4 = 0;
                for (char c : this.mask.toCharArray()) {
                    if (c == '#' || unmask.length() <= this.old.length()) {
                        try {
                            StringBuilder sb = new StringBuilder();
                            sb.append(str);
                            sb.append(unmask.charAt(i4));
                            str = sb.toString();
                            i4++;
                        } catch (Exception unused) {
                        }
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str);
                        sb2.append(c);
                        str = sb2.toString();
                    }
                }
                this.isUpdating = true;
                editText.setText(str);
                editText.setSelection(str.length());
            }
        };
    }

    public static String unmask(String str) {
        return str.replaceAll("[^\\d,]", "").replaceAll("[,]", ".");
    }

    public static String formatarNumerosVotos(int d) {
        try {
            return NumberFormat.getIntegerInstance(new Locale("pt", "BR")).format(d);
        } catch (Exception unused) {
            return "0";
        }
    }

}
