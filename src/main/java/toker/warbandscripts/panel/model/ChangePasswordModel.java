package toker.warbandscripts.panel.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ChangePasswordModel {

    private String currentPassword;

    @Size(min = 3, max = 32)
    @Pattern(regexp = "^[A-Za-z0-9]+")
    private String newPassword;

    @Size(min = 3, max = 32)
    @Pattern(regexp = "^[A-Za-z0-9]+")
    private String newPasswordConfirm;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }
}
