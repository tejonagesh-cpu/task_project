package com.example.nearbyplaces.viewmodel;

import android.util.Log;

import com.example.nearbyplaces.model.ApiInterface;
import com.example.nearbyplaces.model.Webdata;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskViewModel extends ViewModel {

    private MutableLiveData<JsonElement> data;

    public MutableLiveData<JsonElement> getresponseAPI( double latitude , double longitude , String nearbyPlace){
        if (data ==null){
            data = new MutableLiveData<JsonElement>();

        }
        ApiInterface geoApiService = Webdata.getGeoRetrofit().create(ApiInterface.class);

        geoApiService.getAddressComponents(latitude+","+longitude,"10000",nearbyPlace,"true","AIzaSyDlXUFuSssiB-gXUOmYL90rIv3GEF1nljY")
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, final Response<JsonElement> response) {

                        try {

                            data.setValue(response.body());

                        } catch (Exception e) {
                            Log.e("Exception Error", "" + e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        Log.e("onFailure", "" + t.getMessage());
                    }
                });



        return data;
    }
}