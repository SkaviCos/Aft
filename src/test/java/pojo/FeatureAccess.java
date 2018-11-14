package pojo;

public class FeatureAccess {

    private String name;

    private boolean enabled;

    private String expectedText;

    private boolean onboard;

    private boolean faq;

    private boolean beta;

    public FeatureAccess() {
        this.name = "";
        this.enabled = false;
        this.expectedText = "";
        this.onboard = false;
        this.faq = false;
        this.beta = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpectedText() {
        return expectedText;
    }

    public void setExpectedText(String expectedText) {
        this.expectedText = expectedText;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isOnboard() {
        return onboard;
    }

    public void setOnboard(boolean onboard) {
        this.onboard = onboard;
    }

    public boolean isFaq() {
        return faq;
    }

    public void setFaq(boolean faq) {
        this.faq = faq;
    }

    public boolean isBeta() {
        return beta;
    }

    public void setBeta(boolean beta) {
        this.beta = beta;
    }
}
