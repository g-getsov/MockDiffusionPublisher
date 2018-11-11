package com.oddschecker.mockdiffusionpublisher.containers;

import com.oddschecker.mockdiffusionpublisher.enums.ResponseStatus;

public class BaseResponseContainer {

    BaseResponseContainer(ResponseStatus status) {
        this.status = status;
    }

    private ResponseStatus status;

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }
}
