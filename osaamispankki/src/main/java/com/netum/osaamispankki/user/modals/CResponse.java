package com.netum.osaamispankki.user.modals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class  CResponse <T> {
    private T answer;
    private boolean success;
}
