package com.votacerto.model;

public class Politician {
    private Integer id;
    private String name;
    private String picture;
    private String office;
    private String bio;
    private String createdAt;
    private String updatedAt;
    private String cover;
    private Party party;

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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     *
     * @param picture
     * The picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     *
     * @return
     * The office
     */
    public String getOffice() {
        return office;
    }

    /**
     *
     * @param office
     * The office
     */
    public void setOffice(String office) {
        this.office = office;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public Object getCreatedAt() {
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
    public Object getUpdatedAt() {
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
     * The cover
     */
    public String getCover() {
        return this.cover;
    }

    /**
     *
     * @param cover
     * The cover
     */
    public void setCover(String cover) {
        this.cover = cover;
    }

    /**
     *
     * @return
     * The party
     */
    public Party getParty() {
        return party;
    }

    /**
     *
     * @return
     * The bio
     */
    public String getBio() {
        return bio;
    }

    /**
     *
     * @param bio
     * The cover
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     *
     * @param partyId
     * The party
     */
    public void setParty(Party partyId) {
        this.party = partyId;
    }

}