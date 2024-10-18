package com.example.demo.dao.bo.flow;


import lombok.Data;

import java.util.List;

@Data(staticConstructor = "of")
public class FolderFlows extends Folder {
    List<Flow> flows;
}
