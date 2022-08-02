package com.ankush.Link.Preview.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Link {

    private String url;
    private String title;
    private String desc;
    private String image;
}
