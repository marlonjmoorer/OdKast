package com.marlonjmoorer.odkast.Models;

import java.util.Date;
import java.util.Map;

/**
 * Created by marlonmoorer on 6/1/17.
 */

public class PodcastChart {

    /**
     * country : us
     * limit : 10
     * start_date : 2017-05-26
     * shows : {"The Joe Rogan Experience":{"id":363,"ranks":{"2017-05-26":10}},"Wow in the World":{"id":17307,"ranks":{"2017-05-26":9}},"Serial":{"id":15,"ranks":{"2017-05-26":8,"2017-05-27":9,"2017-05-28":8,"2017-05-29":7}},"Pod Save America":{"id":16615,"ranks":{"2017-05-26":7,"2017-05-27":7,"2017-05-28":7,"2017-05-29":5,"2017-05-30":8,"2017-05-31":10}},"TED Radio Hour":{"id":355,"ranks":{"2017-05-26":6,"2017-05-27":6,"2017-05-28":4,"2017-05-29":3,"2017-05-30":5,"2017-05-31":7,"2017-06-01":9}},"This American Life":{"id":27,"ranks":{"2017-05-26":5,"2017-05-27":5,"2017-05-28":2,"2017-05-29":2,"2017-05-30":3,"2017-05-31":5,"2017-06-01":6}},"Up First":{"id":17134,"ranks":{"2017-05-26":4,"2017-05-27":8,"2017-05-28":10,"2017-05-29":9,"2017-05-30":6,"2017-05-31":8,"2017-06-01":8}},"Where Should We Begin? with Esther Perel":{"id":17381,"ranks":{"2017-05-26":3,"2017-05-27":3,"2017-05-28":3,"2017-05-29":4,"2017-05-30":7,"2017-06-01":7}},"The Daily":{"id":16712,"ranks":{"2017-05-26":2,"2017-05-27":2,"2017-05-28":5,"2017-05-29":8,"2017-05-30":4,"2017-05-31":4,"2017-06-01":5}},"S-Town":{"id":17049,"ranks":{"2017-05-26":1,"2017-05-27":1,"2017-05-28":1,"2017-05-29":1,"2017-05-30":1,"2017-05-31":2,"2017-06-01":3}},"Stuff You Should Know":{"id":358,"ranks":{"2017-05-27":10,"2017-05-28":9,"2017-05-29":6,"2017-05-30":9}},"VIEWS with David Dobrik and Jason Nash":{"id":17380,"ranks":{"2017-05-27":4,"2017-05-28":6}},"Fresh Air":{"id":14,"ranks":{"2017-05-29":10}},"Invisibilia":{"id":359,"ranks":{"2017-05-30":10,"2017-05-31":9,"2017-06-01":2}},"Off The Vine with Kaitlyn Bristowe":{"id":17407,"ranks":{"2017-05-30":2,"2017-05-31":1,"2017-06-01":1}},"The Ben and Ashley I Almost Famous Podcast":{"id":17383,"ranks":{"2017-05-31":6,"2017-06-01":4}},"The McElroy Brothers Will Be In Trolls 2":{"id":17412,"ranks":{"2017-05-31":3,"2017-06-01":10}}}
     */

    public String country;
    public String limit;
    public String start_date;
    public Map<String, ShowsBean> shows;

    public static class ShowsBean {


        /**
         * id : 27
         * ranks : {"2016-01-01":3,"2016-01-02":2}
         */

        private int id;
        private Map<Date,Integer> ranks;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Map<Date, Integer> getRanks() {
            return ranks;
        }

        public void setRanks(Map<Date,Integer> ranks) {
            this.ranks = ranks;
        }


    }


}
