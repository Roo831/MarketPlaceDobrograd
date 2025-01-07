package com.poptsov.marketplace.dto;

import java.util.List;

public class PlayerSummaryResponse {

    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response {

        private List<Player> players;

        public List<Player> getPlayers() {
            return players;
        }

        public void setPlayers(List<Player> players) {
            this.players = players;
        }
    }

    public static class Player {

        private String steamid;
        private String personaname;
        private String avatar;
        private String avatarmedium;
        private String avatarfull;
        private String loccountrycode;
        private String lastlogoff;

        public String getSteamid() {
            return steamid;
        }

        public void setSteamid(String steamid) {
            this.steamid = steamid;
        }

        public String getPersonaname() {
            return personaname;
        }

        public void setPersonaname(String personaname) {
            this.personaname = personaname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAvatarmedium() {
            return avatarmedium;
        }

        public void setAvatarmedium(String avatarmedium) {
            this.avatarmedium = avatarmedium;
        }

        public String getAvatarfull() {
            return avatarfull;
        }

        public void setAvatarfull(String avatarfull) {
            this.avatarfull = avatarfull;
        }

        public String getLoccountrycode() {
            return loccountrycode;
        }

        public void setLoccountrycode(String loccountrycode) {
            this.loccountrycode = loccountrycode;
        }

        public String getLastlogoff() {
            return lastlogoff;
        }

        public void setLastlogoff(String lastlogoff) {
            this.lastlogoff = lastlogoff;
        }
    }
}