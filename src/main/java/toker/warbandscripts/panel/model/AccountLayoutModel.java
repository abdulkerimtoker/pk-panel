package toker.warbandscripts.panel.model;

public class AccountLayoutModel {
    private boolean newPasswordIsInvalid;
    private boolean newPasswordNotMatches;
    private boolean newPasswordIsSet;
    private boolean currentPasswordIsWrong;

    public boolean isNewPasswordIsInvalid() {
        return newPasswordIsInvalid;
    }

    public void setNewPasswordIsInvalid(boolean newPasswordIsInvalid) {
        this.newPasswordIsInvalid = newPasswordIsInvalid;
    }

    public boolean isNewPasswordNotMatches() {
        return newPasswordNotMatches;
    }

    public void setNewPasswordNotMatches(boolean newPasswordNotMatches) {
        this.newPasswordNotMatches = newPasswordNotMatches;
    }

    public boolean isNewPasswordIsSet() {
        return newPasswordIsSet;
    }

    public void setNewPasswordIsSet(boolean newPasswordIsSet) {
        this.newPasswordIsSet = newPasswordIsSet;
    }

    public boolean isCurrentPasswordIsWrong() {
        return currentPasswordIsWrong;
    }

    public void setCurrentPasswordIsWrong(boolean currentPasswordIsWrong) {
        this.currentPasswordIsWrong = currentPasswordIsWrong;
    }
}
