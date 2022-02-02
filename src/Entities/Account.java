package Entities;

import java.util.*;

import Exceptions.InformationIncompleteException;

public class Account {
    public Information information;
    public ArrayList<Character> characters;
    public int mapsCompleted;

    public Account(Information information, ArrayList<Character> characters, int mapsCompleted) {
        this.information = information;
        this.characters = characters;
        this.mapsCompleted = mapsCompleted;
    }

    public static class Information {
        private final Credentials credentials;
        private final TreeSet<String> favouriteGames;
        private final String name;
        private final String country;

        private Information(InformationBuilder builder) {
            this.credentials = builder.credentials;
            this.favouriteGames = builder.favouriteGames;
            this.name = builder.name;
            this.country = builder.country;
        }

        public Credentials getCredentials() {
            return credentials;
        }
        public TreeSet<String> getFavouriteGames() {
            return favouriteGames;
        }
        public String getName() {
            return name;
        }
        public String getCountry() {
            return country;
        }

        public static class InformationBuilder {
            private final Credentials credentials;
            private final String name;
            private TreeSet<String> favouriteGames;
            private String country;
            public InformationBuilder(Credentials credentials, String name) {
                this.credentials = credentials;
                this.name = name;
            }
            public InformationBuilder favouriteGames(TreeSet<String> favouriteGames) {
                this.favouriteGames = favouriteGames;
                return this;
            }
            public InformationBuilder country(String country) {
                this.country = country;
                return this;
            }
            public Information build() throws InformationIncompleteException{
                if( credentials == null || name == null ) {
                    throw new InformationIncompleteException();
                }
                return new Information(InformationBuilder.this);
            }
        }
    }
}
