public class Aluno {

    private String nome;
    private double notaMat;
    private double notaPort;
    private double notaGeo;
    private double notaHist;
    private double notaCien;

    public Aluno() {
    }

    public Aluno(String nome, double notaMat, double notaPort,
                double notaGeo, double notaHist, double notaCien) {
        this.nome     = nome;
        this.notaMat  = notaMat;
        this.notaPort = notaPort;
        this.notaGeo  = notaGeo;
        this.notaHist = notaHist;
        this.notaCien = notaCien;
    }

    public String getNome()      { return nome; }
    public double getNotaMat()   { return notaMat; }
    public double getNotaPort()  { return notaPort; }
    public double getNotaGeo()   { return notaGeo; }
    public double getNotaHist()  { return notaHist; }
    public double getNotaCien()  { return notaCien; }

    public void setNome(String nome)         { this.nome = nome; }
    public void setNotaMat(double notaMat)   { this.notaMat = notaMat; }
    public void setNotaPort(double notaPort) { this.notaPort = notaPort; }
    public void setNotaGeo(double notaGeo)   { this.notaGeo = notaGeo; }
    public void setNotaHist(double notaHist) { this.notaHist = notaHist; }
    public void setNotaCien(double notaCien) { this.notaCien = notaCien; }

    public double calcularMedia() {
        return (notaMat + notaPort + notaGeo + notaHist + notaCien) / 5.0;
    }

    public String verificarSituacao() {
        double media = calcularMedia();
        boolean todasAprovadas = notaMat  >= 6.0 && notaPort >= 6.0
        && notaGeo  >= 6.0 && notaHist >= 6.0
        && notaCien >= 6.0;
        if (todasAprovadas) return "APROVADO";
        if (media >= 6.0)   return "RECUPERAÇÃO";
        return "REPROVADO";
    }

    @Override
    public String toString() {
        return String.format("Aluno: %s | Media: %.2f | Situacao: %s",
                nome, calcularMedia(), verificarSituacao());
    }
}