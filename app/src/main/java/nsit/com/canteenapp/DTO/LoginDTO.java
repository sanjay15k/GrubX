package nsit.com.canteenapp.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by starhawk on 01/08/18.
 */

public class LoginDTO implements Parcelable{

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String fullName;
    @SerializedName("phoneNumber")
    @Expose
    private String mobileNumber;
    @Expose
    @SerializedName("MobVerified")
    private boolean isMobileVerified;

    public LoginDTO(String username, String password){
        this.username = username;
        this.password = password;
    }

    public LoginDTO(Parcel source) {
        this.username = source.readString();
        this.password = source.readString();
        this.email = source.readString();
        this.fullName = source.readString();
        this.mobileNumber = source.readString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public boolean isMobileVerified() {
        return isMobileVerified;
    }

    public void setMobileVerified(boolean mobileVerified) {
        isMobileVerified = mobileVerified;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", isMobileVerified=" + isMobileVerified +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeString(fullName);
        dest.writeString(mobileNumber);
    }

    public static final Parcelable.Creator<LoginDTO> CREATOR = new Parcelable.Creator<LoginDTO>() {

        @Override
        public LoginDTO createFromParcel(Parcel source) {
            return new LoginDTO(source);
        }

        @Override
        public LoginDTO[] newArray(int size) {
            return new LoginDTO[size];
        }
    };
    
}
