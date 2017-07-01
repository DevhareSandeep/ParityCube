package demo.paritycube.com.deals.pojo;

/**
 * Created by Sandeep Devhare @APAR on 6/22/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Merchant {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("permalink")
    @Expose
    private String permalink;
    @SerializedName("recommendation")
    @Expose
    private Integer recommendation;
    @SerializedName("recommendation_flag")
    @Expose
    private Boolean recommendationFlag;
    @SerializedName("average_rating")
    @Expose
    private String averageRating;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public Integer getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(Integer recommendation) {
        this.recommendation = recommendation;
    }

    public Boolean getRecommendationFlag() {
        return recommendationFlag;
    }

    public void setRecommendationFlag(Boolean recommendationFlag) {
        this.recommendationFlag = recommendationFlag;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

}