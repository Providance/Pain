package pain;

public class Iban {

    public static boolean isValidIban(String iban) {
        if (iban == null) return false;

        iban = iban.replaceAll("\\s+", "").toUpperCase();

        if (iban.length() < 15 || iban.length() > 34) {
            return false;
        }

        String rearranged = iban.substring(4) + iban.substring(0, 4);
        StringBuilder numericIban = new StringBuilder();
        for (char c : rearranged.toCharArray()) {
            if (Character.isDigit(c)) {
                numericIban.append(c);
            } else if (Character.isLetter(c)) {
                numericIban.append((int) c - 55); // 'A' = 65 → 10
            } else {
                return false;
            }
        }

        String temp = numericIban.toString();
        int mod = 0;

        for (int i = 0; i < temp.length(); i++) {
            mod = (mod * 10 + (temp.charAt(i) - '0')) % 97;
        }

        return mod == 1;
    }
}

