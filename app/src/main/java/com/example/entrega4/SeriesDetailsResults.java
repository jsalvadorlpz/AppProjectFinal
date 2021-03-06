package com.example.entrega4;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeriesDetailsResults {
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("created_by")
    private List<CreatedBy> createdBy = null;
    @SerializedName("episode_run_time")
    private List<Integer> episodeRunTime = null;
    @SerializedName("first_air_date")
    private String firstAirDate;
    @SerializedName("genres")
    private List<Genre> genres = null;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("id")
    private Integer id;
    @SerializedName("in_production")
    private Boolean inProduction;
    @SerializedName("languages")
    private List<String> languages = null;
    @SerializedName("last_air_date")
    private String lastAirDate;
    @SerializedName("last_episode_to_air")
    private LastEpisodeToAir lastEpisodeToAir;
    @SerializedName("name")
    private String name;
    @SerializedName("next_episode_to_air")
    private Object nextEpisodeToAir;
    @SerializedName("networks")
    private List<Network> networks = null;
    @SerializedName("number_of_episodes")
    private Integer numberOfEpisodes;
    @SerializedName("number_of_seasons")
    private Integer numberOfSeasons;
    @SerializedName("origin_country")
    private List<String> originCountry = null;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_name")
    private String originalName;
    @SerializedName("overview")
    private String overview;
    @SerializedName("popularity")
    private Double popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("production_companies")
    private List<ProductionCompany> productionCompanies = null;
    @SerializedName("production_countries")
    private List<ProductionCountry> productionCountries = null;
    @SerializedName("seasons")
    private List<Season> seasons = null;
    @SerializedName("spoken_languages")
    private List<SpokenLanguage> spokenLanguages = null;
    @SerializedName("status")
    private String status;
    @SerializedName("tagline")
    private String tagline;
    @SerializedName("type")
    private String type;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("vote_count")
    private Integer voteCount;

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public List<CreatedBy> getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(List<CreatedBy> createdBy) {
        this.createdBy = createdBy;
    }

    public List<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    public void setEpisodeRunTime(List<Integer> episodeRunTime) {
        this.episodeRunTime = episodeRunTime;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getInProduction() {
        return inProduction;
    }

    public void setInProduction(Boolean inProduction) {
        this.inProduction = inProduction;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public LastEpisodeToAir getLastEpisodeToAir() {
        return lastEpisodeToAir;
    }

    public void setLastEpisodeToAir(LastEpisodeToAir lastEpisodeToAir) {
        this.lastEpisodeToAir = lastEpisodeToAir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getNextEpisodeToAir() {
        return nextEpisodeToAir;
    }

    public void setNextEpisodeToAir(Object nextEpisodeToAir) {
        this.nextEpisodeToAir = nextEpisodeToAir;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

    public Integer getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(Integer numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public Integer getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(Integer numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public List<SpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public class CreatedBy {
        @SerializedName("id")
        private Integer id;
        @SerializedName("credit_id")
        private String creditId;
        @SerializedName("name")
        private String name;
        @SerializedName("gender")
        private Integer gender;
        @SerializedName("profile_path")
        private String profilePath;
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public String getCreditId() {
            return creditId;
        }
        public void setCreditId(String creditId) {
            this.creditId = creditId;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Integer getGender() {
            return gender;
        }
        public void setGender(Integer gender) {
            this.gender = gender;
        }
        public String getProfilePath() {
            return profilePath;
        }
        public void setProfilePath(String profilePath) {
            this.profilePath = profilePath;
        }
    }
    public class Genre {
        @SerializedName("id")
        private Integer id;
        @SerializedName("name")
        private String name;

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
    }
    public class LastEpisodeToAir {
        @SerializedName("air_date")
        private String airDate;
        @SerializedName("episode_number")
        private Integer episodeNumber;
        @SerializedName("id")
        private Integer id;
        @SerializedName("name")
        private String name;
        @SerializedName("overview")
        private String overview;
        @SerializedName("production_code")
        private String productionCode;
        @SerializedName("season_number")
        private Integer seasonNumber;
        @SerializedName("still_path")
        private String stillPath;
        @SerializedName("vote_average")
        private Double voteAverage;
        @SerializedName("vote_count")
        private Integer voteCount;
        public String getAirDate() {
            return airDate;
        }
        public void setAirDate(String airDate) {
            this.airDate = airDate;
        }
        public Integer getEpisodeNumber() {
            return episodeNumber;
        }
        public void setEpisodeNumber(Integer episodeNumber) {
            this.episodeNumber = episodeNumber;
        }
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
        public String getOverview() {
            return overview;
        }
        public void setOverview(String overview) {
            this.overview = overview;
        }
        public String getProductionCode() {
            return productionCode;
        }
        public void setProductionCode(String productionCode) {
            this.productionCode = productionCode;
        }
        public Integer getSeasonNumber() {
            return seasonNumber;
        }
        public void setSeasonNumber(Integer seasonNumber) {
            this.seasonNumber = seasonNumber;
        }
        public String getStillPath() {
            return stillPath;
        }
        public void setStillPath(String stillPath) {
            this.stillPath = stillPath;
        }
        public Double getVoteAverage() {
            return voteAverage;
        }
        public void setVoteAverage(Double voteAverage) {
            this.voteAverage = voteAverage;
        }
        public Integer getVoteCount() {
            return voteCount;
        }
        public void setVoteCount(Integer voteCount) {
            this.voteCount = voteCount;
        }
    }

    public class Network {

        @SerializedName("name")
        private String name;
        @SerializedName("id")
        private Integer id;
        @SerializedName("logo_path")
        private String logoPath;
        @SerializedName("origin_country")
        private String originCountry;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public String getLogoPath() {
            return logoPath;
        }
        public void setLogoPath(String logoPath) {
            this.logoPath = logoPath;
        }
        public String getOriginCountry() {
            return originCountry;
        }
        public void setOriginCountry(String originCountry) {
            this.originCountry = originCountry;
        }
    }

    public class ProductionCompany {

        @SerializedName("id")
        private Integer id;
        @SerializedName("logo_path")
        private Object logoPath;
        @SerializedName("name")
        private String name;
        @SerializedName("origin_country")
        private String originCountry;
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public Object getLogoPath() {
            return logoPath;
        }
        public void setLogoPath(Object logoPath) {
            this.logoPath = logoPath;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getOriginCountry() {
            return originCountry;
        }
        public void setOriginCountry(String originCountry) {
            this.originCountry = originCountry;
        }
    }

    public class ProductionCountry {

        @SerializedName("iso_3166_1")
        private String iso31661;
        @SerializedName("name")
        private String name;

        public String getIso31661() {
            return iso31661;
        }
        public void setIso31661(String iso31661) {
            this.iso31661 = iso31661;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }


    public class Season {

        @SerializedName("air_date")
        private String airDate;
        @SerializedName("episode_count")
        private Integer episodeCount;
        @SerializedName("id")
        private Integer id;
        @SerializedName("name")
        private String name;
        @SerializedName("overview")
        private String overview;
        @SerializedName("poster_path")
        private String posterPath;
        @SerializedName("season_number")
        private Integer seasonNumber;

        public String getAirDate() {
            return airDate;
        }
        public void setAirDate(String airDate) {
            this.airDate = airDate;
        }
        public Integer getEpisodeCount() {
            return episodeCount;
        }
        public void setEpisodeCount(Integer episodeCount) {
            this.episodeCount = episodeCount;
        }
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
        public String getOverview() {
            return overview;
        }
        public void setOverview(String overview) {
            this.overview = overview;
        }
        public String getPosterPath() {
            return posterPath;
        }
        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }
        public Integer getSeasonNumber() {
            return seasonNumber;
        }
        public void setSeasonNumber(Integer seasonNumber) {
            this.seasonNumber = seasonNumber;
        }
    }

    public class SpokenLanguage {

        @SerializedName("english_name")
        private String englishName;
        @SerializedName("iso_639_1")
        private String iso6391;
        @SerializedName("name")
        private String name;

        public String getEnglishName() {
            return englishName;
        }
        public void setEnglishName(String englishName) {
            this.englishName = englishName;
        }
        public String getIso6391() {
            return iso6391;
        }

        public void setIso6391(String iso6391) {
            this.iso6391 = iso6391;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}