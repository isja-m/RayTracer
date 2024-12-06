class Pixel {
    final int xCoord;
    final int yCoord;
    private Vector brightnesses;

    public Pixel(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.brightnesses = new Vector(0,0, 0);
    }

    public void addToBrightness(Vector brightnesses) {
        this.brightnesses = this.brightnesses.add(brightnesses);
    }

    public double[] getBrightness() {
        return new double[] {brightnesses.xCoord, brightnesses.yCoord, brightnesses.zCoord};
    }
}
