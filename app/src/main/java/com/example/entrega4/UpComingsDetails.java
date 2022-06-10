package com.example.entrega4;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpComingsDetails {
    @SerializedName("page")
    private Integer page;
    @SerializedName("results")
    private List<Result> results = null;
    @SerializedName("dates")
    private Dates dates;
    @SerializedName("total_pages")
    private Integer totalPages;
    @SerializedName("total_results")
    private Integer totalResults;

    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    public List<Result> getResults() {
        return results;
    }
    public void setResults(List<Result> results) {
        this.results = results;
    }
    public Dates getDates() {
        return dates;
    }
    public void setDates(Dates dates) {
        this.dates = dates;
    }
    public Integer getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
    public Integer getTotalResults() {
        return totalResults;
    }
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
    public class Dates {

        @SerializedName("maximum")
        private String maximum;
        @SerializedName("minimum")
        private String minimum;

        public String getMaximum() {
            return maximum;
        }

        public void setMaximum(String maximum) {
            this.maximum = maximum;
        }

        public String getMinimum() {
            return minimum;
        }
        public void setMinimum(String minimum) {
            this.minimum = minimum;
        }

    }
    public class Result {

        @SerializedName("poster_path")
        private String posterPath;
        @SerializedName("adult")
        private Boolean adult;
        @SerializedName("overview")
        private String overview;
        @SerializedName("release_date")
        private String releaseDate;
        @SerializedName("genre_ids")
        private List<Integer> genreIds = null;
        @SerializedName("id")
        private Integer id;
        @SerializedName("original_title")
        private String originalTitle;
        @SerializedName("original_language")
        private String originalLanguage;
        @SerializedName("title")
        private String title;
        @SerializedName("backdrop_path")
        private String backdropPath;
        @SerializedName("popularity")
        private Double popularity;
        @SerializedName("vote_count")
        private Integer voteCount;
        @SerializedName("video")
        private Boolean video;
        @SerializedName("vote_average")
        private Integer voteAverage;

        public String getPosterPath() {
            return posterPath;
        }
        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }
        public Boolean getAdult() {
            return adult;
        }
        public void setAdult(Boolean adult) {
            this.adult = adult;
        }
        public String getOverview() {
            return overview;
        }
        public void setOverview(String overview) {
            this.overview = overview;
        }
        public String getReleaseDate() {
            return releaseDate;
        }
        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }
        public List<Integer> getGenreIds() {
            return genreIds;
        }
        public void setGenreIds(List<Integer> genreIds) {
            this.genreIds = genreIds;
        }
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public String getOriginalTitle() {
            return originalTitle;
        }
        public void setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
        }
        public String getOriginalLanguage() {
            return originalLanguage;
        }
        public void setOriginalLanguage(String originalLanguage) {
            this.originalLanguage = originalLanguage;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getBackdropPath() {
            return backdropPath;
        }
        public void setBackdropPath(String backdropPath) {
            this.backdropPath = backdropPath;
        }
        public Double getPopularity() {
            return popularity;
        }
        public void setPopularity(Double popularity) {
            this.popularity = popularity;
        }
        public Integer getVoteCount() {
            return voteCount;
        }
        public void setVoteCount(Integer voteCount) {
            this.voteCount = voteCount;
        }
        public Boolean getVideo() {
            return video;
        }
        public void setVideo(Boolean video) {
            this.video = video;
        }
        public Integer getVoteAverage() {
            return voteAverage;
        }
        public void setVoteAverage(Integer voteAverage) {
            this.voteAverage = voteAverage;
        }

    }
}
