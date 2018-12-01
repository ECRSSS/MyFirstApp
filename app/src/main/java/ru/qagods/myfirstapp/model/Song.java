package ru.qagods.myfirstapp.model;

import java.io.Serializable;

public class Song implements Serializable {

    /**
     * data : {"id":0,"name":"string","duration":"string"}
     */

        private int id;
        private String name;
        private String duration;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
}
