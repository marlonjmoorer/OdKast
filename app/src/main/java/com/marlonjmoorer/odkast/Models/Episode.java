package com.marlonjmoorer.odkast.Models;

import java.util.List;

/**
 * Created by marlonmoorer on 6/3/17.
 */

public class Episode {

    /**
     * id : 256837
     * title : "Post-Election Attitude" Episode 11
     * description :
     * date_created : 2016-11-10
     * identifier : 9b916cf446f7f97c88a903f78a9cd646
     * digital_location : http://atayloredadventuretohappiness.libsyn.com/post-election-attitude-episode-11
     * physical_location : rss
     * duration : 445
     * tags : []
     * updated_at : 2017-01-05 21:07:20 UTC
     * itunes_episode : 377697047
     * buzz_score : 0.444936960970464533333333333333333333
     * date_added : 2016-11-10 08:55:08 UTC
     * show_id : 6926
     * show_title : A Taylored Adventure To Happiness
     * audio_files : [{"id":255038,"duration":445,"url":"http://traffic.libsyn.com/atayloredadventuretohappiness/November9Episode.mp3?dest-id=308997"}]
     * image_files : [{"url":{"full":"https://www.audiosear.ch/media/a41a34bfc3b2e55d535e1c12c630f925/0/public/image_file/99686/Taylor_Podcast-1400x1400.jpg","thumb":"https://www.audiosear.ch/media/f2dcd855b1c611e7eeecf2d2041640d8/0/thumb/image_file/99686/Taylor_Podcast-1400x1400.jpg"}}]
     * rss_url : http://atayloredadventuretohappiness.libsyn.com/rss
     * extra : {"skip_transcript":"true"}
     * urls : {"self":"https://www.audiosear.ch/api/episodes/256837","ui":"https://www.audiosear.ch/a/3eb45/post-election-attitude-episode-11"}
     * categories : [{"id":24,"parent_id":47,"name":"Self-Help","parent_name":"Health"},{"id":47,"parent_id":null,"name":"Health","parent_name":null},{"id":6,"parent_id":null,"name":"Arts","parent_name":null},{"id":39,"parent_id":6,"name":"Fashion & Beauty","parent_name":"Arts"},{"id":49,"parent_id":47,"name":"Fitness & Nutrition","parent_name":"Health"}]
     * highlights : {}
     * entities : []
     */

    private int id;
    private String title;
    private String description;
    private String date_created;
    private String identifier;
    private String digital_location;
    private String physical_location;
    private int duration;
    private String updated_at;
    private int itunes_episode;
    private String buzz_score;
    private String date_added;
    private int show_id;
    private String show_title;
    private String rss_url;
    private ExtraBean extra;
    private UrlsBean urls;
    private HighlightsBean highlights;
    private List<?> tags;
    private List<AudioFilesBean> audio_files;
    private List<ImageFilesBean> image_files;
    private List<CategoriesBean> categories;
    private List<?> entities;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDigital_location() {
        return digital_location;
    }

    public void setDigital_location(String digital_location) {
        this.digital_location = digital_location;
    }

    public String getPhysical_location() {
        return physical_location;
    }

    public void setPhysical_location(String physical_location) {
        this.physical_location = physical_location;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getItunes_episode() {
        return itunes_episode;
    }

    public void setItunes_episode(int itunes_episode) {
        this.itunes_episode = itunes_episode;
    }

    public String getBuzz_score() {
        return buzz_score;
    }

    public void setBuzz_score(String buzz_score) {
        this.buzz_score = buzz_score;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public int getShow_id() {
        return show_id;
    }

    public void setShow_id(int show_id) {
        this.show_id = show_id;
    }

    public String getShow_title() {
        return show_title;
    }

    public void setShow_title(String show_title) {
        this.show_title = show_title;
    }

    public String getRss_url() {
        return rss_url;
    }

    public void setRss_url(String rss_url) {
        this.rss_url = rss_url;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public UrlsBean getUrls() {
        return urls;
    }

    public void setUrls(UrlsBean urls) {
        this.urls = urls;
    }

    public HighlightsBean getHighlights() {
        return highlights;
    }

    public void setHighlights(HighlightsBean highlights) {
        this.highlights = highlights;
    }

    public List<?> getTags() {
        return tags;
    }

    public void setTags(List<?> tags) {
        this.tags = tags;
    }

    public List<AudioFilesBean> getAudio_files() {
        return audio_files;
    }

    public void setAudio_files(List<AudioFilesBean> audio_files) {
        this.audio_files = audio_files;
    }

    public List<ImageFilesBean> getImage_files() {
        return image_files;
    }

    public void setImage_files(List<ImageFilesBean> image_files) {
        this.image_files = image_files;
    }

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        this.categories = categories;
    }

    public List<?> getEntities() {
        return entities;
    }

    public void setEntities(List<?> entities) {
        this.entities = entities;
    }

    public static class ExtraBean {
        /**
         * skip_transcript : true
         */

        private String skip_transcript;

        public String getSkip_transcript() {
            return skip_transcript;
        }

        public void setSkip_transcript(String skip_transcript) {
            this.skip_transcript = skip_transcript;
        }
    }

    public static class UrlsBean {
        /**
         * self : https://www.audiosear.ch/api/episodes/256837
         * ui : https://www.audiosear.ch/a/3eb45/post-election-attitude-episode-11
         */

        private String self;
        private String ui;

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }

        public String getUi() {
            return ui;
        }

        public void setUi(String ui) {
            this.ui = ui;
        }
    }

    public static class HighlightsBean {
    }

    public static class AudioFilesBean {
        /**
         * id : 255038
         * duration : 445
         * url : http://traffic.libsyn.com/atayloredadventuretohappiness/November9Episode.mp3?dest-id=308997
         */

        private int id;
        private int duration;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class ImageFilesBean {
        /**
         * url : {"full":"https://www.audiosear.ch/media/a41a34bfc3b2e55d535e1c12c630f925/0/public/image_file/99686/Taylor_Podcast-1400x1400.jpg","thumb":"https://www.audiosear.ch/media/f2dcd855b1c611e7eeecf2d2041640d8/0/thumb/image_file/99686/Taylor_Podcast-1400x1400.jpg"}
         */

        private UrlBean url;

        public UrlBean getUrl() {
            return url;
        }

        public void setUrl(UrlBean url) {
            this.url = url;
        }

        public static class UrlBean {
            /**
             * full : https://www.audiosear.ch/media/a41a34bfc3b2e55d535e1c12c630f925/0/public/image_file/99686/Taylor_Podcast-1400x1400.jpg
             * thumb : https://www.audiosear.ch/media/f2dcd855b1c611e7eeecf2d2041640d8/0/thumb/image_file/99686/Taylor_Podcast-1400x1400.jpg
             */

            private String full;
            private String thumb;

            public String getFull() {
                return full;
            }

            public void setFull(String full) {
                this.full = full;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }
        }
    }

    public static class CategoriesBean {
        /**
         * id : 24
         * parent_id : 47
         * name : Self-Help
         * parent_name : Health
         */

        private int id;
        private int parent_id;
        private String name;
        private String parent_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParent_name() {
            return parent_name;
        }

        public void setParent_name(String parent_name) {
            this.parent_name = parent_name;
        }
    }
}
