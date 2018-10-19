package nsit.com.canteenapp.DTO;

/**
 * Created by starhawk on 12/08/18.
 */

public class OurTeamProfileDTO {

    private int profileImage;
    private String memberName;
    private String memberPosition;
    private String memberFacebook;
    private String memberEmail;
    private String memberWhatsapp;
    private String fbProfileID;

    public OurTeamProfileDTO(int profileImage, String memberName, String memberPosition, String memberFacebook, String memberEmail, String memberWhatsapp, String fbProfileID) {
        this.profileImage = profileImage;
        this.memberName = memberName;
        this.memberPosition = memberPosition;
        this.memberFacebook = memberFacebook;
        this.memberEmail = memberEmail;
        this.memberWhatsapp = memberWhatsapp;
        this.fbProfileID = fbProfileID;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPosition() {
        return memberPosition;
    }

    public void setMemberPosition(String memberPosition) {
        this.memberPosition = memberPosition;
    }

    public String getMemberFacebook() {
        return memberFacebook;
    }

    public void setMemberFacebook(String memberFacebook) {
        this.memberFacebook = memberFacebook;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberWhatsapp() {
        return memberWhatsapp;
    }

    public void setMemberWhatsapp(String memberWhatsapp) {
        this.memberWhatsapp = memberWhatsapp;
    }

    public String getFbProfileID() {
        return fbProfileID;
    }

    public void setFbProfileID(String fbProfileID) {
        this.fbProfileID = fbProfileID;
    }
}
