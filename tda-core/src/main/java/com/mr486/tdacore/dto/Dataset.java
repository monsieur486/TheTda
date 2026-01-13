package com.mr486.tdacore.dto;

import com.mr486.tdacore.configuration.ApplicationConfiguration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dataset {
    private String label;
    private List<Integer> data;
    private int borderwith;
}
