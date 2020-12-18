package com.example.precis;

import android.os.Build;
import java.io.Serializable;
import androidx.annotation.RequiresApi;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public class Summary implements Serializable {

        public String urlText;
        public String summaryText;

        Summary(String urlText, String summaryText) {
            this.urlText = urlText;
            this.summaryText = summaryText;
            
        }
        
        Summary(String urlText)
        {
            this.urlText = urlText;
            this.summaryText = "Processing";
        }

        public Summary(){ }



        String getUrlText() {
            return urlText;
        }

        public void setUrlText(String urlText) {
            this.urlText = urlText;
        }

        String getSummaryText() {
            return summaryText;
        }

        public void setSummaryText(String summaryText) {
            this.summaryText = summaryText;
        }


    }
