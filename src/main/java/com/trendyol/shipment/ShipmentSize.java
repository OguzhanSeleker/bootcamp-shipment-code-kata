package com.trendyol.shipment;

public enum ShipmentSize {

    SMALL(0),
    MEDIUM(1),
    LARGE(2),
    X_LARGE(3);

    private final int index;

    ShipmentSize(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public ShipmentSize GetNextLargerShipmentSize() {
        return ShipmentSize.values()[GetNextLargerShipmentSizeIndex()];
    }
    public int GetNextLargerShipmentSizeIndex(){
        return this.getIndex() == ShipmentSize.values().length - 1 ? this.getIndex() : (this.getIndex() + 1);
    }
}
