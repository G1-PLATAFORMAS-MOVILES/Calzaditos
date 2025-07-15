import android.content.Context
import android.util.Log
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.calzaditos.android.services.BaseService
import org.json.JSONObject


class UserService : BaseService() {

    val resource = "User/"

    fun login(username: String, password: String, context: Context, callback: (Boolean) -> Unit) {
        val url = "${ApiBaseURL}${resource}Login?userName=${username}&password=${password}"

        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener { response ->
                val sharedPreferences = context.getSharedPreferences("calzaditos", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                var user = response.getJSONObject("data")
                editor.putInt("userId",user.getInt("id"))
                editor.putString("userName",user.getString("fullName"))
                editor.apply();
                callback(true)
            },
            Response.ErrorListener { error ->
                if (error.networkResponse?.statusCode == 401) {
                    callback(false)
                } else {
                    Log.e("VolleyError", error.toString())
                    callback(false)
                }
            }
        ) { }

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsonObjectRequest)
    }

    fun registerUser(username: String, fullName: String, password: String, context: Context, callback: (Boolean) -> Unit){
        val url = "${ApiBaseURL}${resource}"
        var userJsonObj = JSONObject()
        userJsonObj.put("userName", username)
        userJsonObj.put("fullName", fullName)
        userJsonObj.put("password", password)

        val requestBody = userJsonObj.toString()

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                val success = response.trim().equals("true", ignoreCase = true)
                callback(success)
            },
            Response.ErrorListener { error ->
                Log.e("VolleyError", error.toString())
                callback(false)
            }
        ) {
            override fun getBody(): ByteArray {
                return requestBody.toByteArray(Charsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }
}