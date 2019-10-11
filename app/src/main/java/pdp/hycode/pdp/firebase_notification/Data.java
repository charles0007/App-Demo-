package pdp.hycode.pdp.firebase_notification;

public class Data {
    private String mess;
    private String title;
    private String type;
    private  String image;
    private  String userId;
private String distKm,sex,email,page;
    public Data(String title,String mess,String type,String image){
        this.title=title;
        this.mess=mess;
        this.type=type;
        this.image=image;
    }
    public Data(){    }


    public String getImage() {
        return image;
    }

    public void setImage(String chatWithImage) {
        this.image = chatWithImage;
    }
    public void setUserId(String chatWithId) {
        this.userId = chatWithId;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Data{" +
                "mess='" + mess + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", chatWithImage='" + image + '\'' +
                ", chatWithId='" + userId + '\'' +
                '}';
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDistKm(String distKm) {
        this.distKm=distKm;
    }

    public void setSex(String sex) {
        this.sex=sex;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
