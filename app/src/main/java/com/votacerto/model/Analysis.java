package com.votacerto.model;

public class Analysis {
    private Integer id;
    private String sentiment;
    private String createdAt;
    private String updatedAt;
    private Integer userId;
    private Integer tweetId;
    private Integer politicianId;
    private Tweet tweet;
    private Politician politician;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The sentiment
     */
    public String getSentiment() {
        return sentiment;
    }

    /**
     *
     * @param sentiment
     * The sentiment
     */
    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The tweetId
     */
    public Integer getTweetId() {
        return tweetId;
    }

    /**
     *
     * @param tweetId
     * The tweetId
     */
    public void setTweetId(Integer tweetId) {
        this.tweetId = tweetId;
    }

    /**
     *
     * @return
     * The politicianId
     */
    public Integer getPoliticianId() {
        return politicianId;
    }

    /**
     *
     * @param politicianId
     * The politicianId
     */
    public void setPoliticianId(Integer politicianId) {
        this.politicianId = politicianId;
    }

    /**
     *
     * @return
     * The tweet
     */
    public Tweet getTweet() {
        return tweet;
    }

    /**
     *
     * @param tweet
     * The tweet
     */
    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    /**
     *
     * @return
     * The politician
     */
    public Politician getPolitician() {
        return politician;
    }

    /**
     *
     * @param politician
     * The politician
     */
    public void setPolitician(Politician politician) {
        this.politician = politician;
    }

}