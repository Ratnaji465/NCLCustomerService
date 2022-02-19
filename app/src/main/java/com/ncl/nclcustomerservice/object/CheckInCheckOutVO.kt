
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CheckInCheckOutVO : Serializable {
    @SerializedName("cs_dailyreport_id")
    @Expose
    var csDailyreportId: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("datetime")
    @Expose
    var datetime: String? = null
}