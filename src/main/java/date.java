public enum date{
    January (31),February (28),March (31),April (30),May (31),June (31),July (31),August (31),September (30),October (31),November (30),December (31);

    private int i;

    date(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }
}
