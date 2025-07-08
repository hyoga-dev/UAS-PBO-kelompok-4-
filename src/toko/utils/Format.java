/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package toko.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Format {
    public static String formatRibuan(int angka) {
        DecimalFormatSymbols simbol = new DecimalFormatSymbols();
        simbol.setGroupingSeparator('.'); // pakai titik, bukan koma

        DecimalFormat format = new DecimalFormat("#,###", simbol);
        return format.format(angka);
    }
    
    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
