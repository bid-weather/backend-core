package com.bidweather.backend_core.infra.external.service;

public enum BidApiType {
    CNSTWK(1L, "/getBidPblancListInfoCnstwk"),
    SERVC(null, "/getBidPblancListInfoServc"),
    THNG(4L, "/getBidPblancListInfoThng");

    private final Long categoryId;
    private final String path;

    BidApiType(Long categoryId, String path) {
        this.categoryId = categoryId;
        this.path = path;
    }

    public Long getCategoryId() { return categoryId; }
    public String getPath() { return path; }
}