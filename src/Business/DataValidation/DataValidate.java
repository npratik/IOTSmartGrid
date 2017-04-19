/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.DataValidation;

import java.util.regex.Pattern;

/**
 *
 * @author Pratik
 */
public class DataValidate {
   
    final static Pattern patternString = Pattern.compile("^[A-Za-z,. ]++$");
    final static Pattern patternAlphaNumeric = Pattern.compile("^[A-Za-z0-9,._ ]++$");
    final static Pattern patternEmail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    final static Pattern patternMobile = Pattern.compile("^[+0-9.()-]{10,25}$");
    final static Pattern patternDate = Pattern.compile("^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$");
    final static Pattern age = Pattern.compile("^[1-9]?[0-9]{1}$|^100$");
    final static Pattern integer1=Pattern.compile("^[-+]?\\d+$");
     public static boolean checkinteger(String text) {
        return integer1.matcher(text).matches();
    }
    public static boolean checkMobilePattern(String text) {
        return patternMobile.matcher(text).matches();
    }

    public static boolean checkStringPattern(String text) {
        return patternString.matcher(text).matches();
    }

    public static boolean checkAlphaStringPattern(String text) {
        return patternAlphaNumeric.matcher(text).matches();
    }

    public static boolean validateEmail(String text) {
        return patternEmail.matcher(text).matches();
    }

    public static boolean validateAge(String text) {
        return age.matcher(text).matches();
    }

    public static boolean validateDate(String text) {
        return patternDate.matcher(text).matches();
    }

    public static boolean validateSSN(String ssn) {
        boolean valid = false;
        if (ssn.length() == 11) {
            if ((ssn.charAt(3) == '-') & (ssn.charAt(6) == '-')) {
                String str = ssn.replaceAll("-", "");
                if (str.matches("[0-9]+") & str.length() == 9) {
                    valid = true;
                }
            }
        }
        return valid;
    }
}
