package demo.paritycube.com.deals.pojo;

/**
 * Created by Sandeep Devhare @APAR on 6/22/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Deals {

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}