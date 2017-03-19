package person.practice.model;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * Created by Evan Hung on 2016/12/27.
 */
@Entity
public class GoodsType {
    @Id
    private String typeName;
    private String typeLabel;
    private String image;
    private String banner;
    private String intro;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(String typeLabel) {
        this.typeLabel = typeLabel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
