package ma.ac.ensias.controller.attribute;

public enum LocaleValue {
    RU("ru"),
    EN("en");

    private final String locale;

    LocaleValue(String locale) {
        this.locale = locale;
    }

    public String getLocale() {
        return locale;
    }
}
