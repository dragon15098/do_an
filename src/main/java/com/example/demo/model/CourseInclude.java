package com.example.demo.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseInclude extends BaseModel {

    private String content;
    private CourseIncludeType type;

    public enum CourseIncludeType {
        TIME_LEARN(0), ARTICLES(1), RESOURCES(2), ACCESS(3), CERTIFICATE(4);

        private int value;

        /**
         * @param value
         */
        CourseIncludeType(int value) {
            throw new UnsupportedOperationException();
        }

        public int getValue() {
            return this.value;
        }

    }

}
