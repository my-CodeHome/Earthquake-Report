package com.example.sunamialert;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Earthquake> {
    public CustomAdapter(@NonNull Context context,  @NonNull List<Earthquake> earthquakeList) {
        super(context, 0, earthquakeList);
    }
    private static final String LOCATION_SEPARATOR = " of ";
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
         View customListViewItem = convertView;

         if(customListViewItem==null)
         {
             customListViewItem = LayoutInflater.from(getContext()).inflate(R.layout.custom_layout,parent,false);
         }
         Earthquake currentEarthquake = getItem(position);


         ///Displaying Magnitude
        TextView magnitudeTextView = customListViewItem.findViewById(R.id.magnitude);
        String formattedMagnitude = formatMagnitude(currentEarthquake.getMagnitude());

          magnitudeTextView.setText(formattedMagnitude);
        GradientDrawable gradientDrawable = (GradientDrawable) magnitudeTextView.getBackground();
         int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
           gradientDrawable.setColor(magnitudeColor);

        String originalLocation = currentEarthquake.getLocation();
        String primaryLocation;
        String locationOffset;
          // Converting the place string into two strings i.e location offset and primary location
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = "Near The";
            primaryLocation = originalLocation;
        }
       /// Displaying offset Location
        TextView offsetLocation = customListViewItem.findViewById(R.id.location_offset);
         offsetLocation.setText(locationOffset);

         //Displaying primary Location
         TextView  PrimaryLocation = customListViewItem.findViewById(R.id.primary_location);
         PrimaryLocation.setText(primaryLocation);

         //Displaying the Date
        Date dateObject = new Date(currentEarthquake.getDate());
        TextView dateView = (TextView) customListViewItem.findViewById(R.id.date);
        String formattedDate = formatDate(dateObject);
        dateView.setText(formattedDate);

        //Displaying Time
        TextView timeView = (TextView) customListViewItem.findViewById(R.id.time);
        String formattedTime = formatTime(dateObject);
        timeView.setText(formattedTime);

        return customListViewItem;
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
}
