import java.util.Random;

public class Synapse {
	
	public static Random random;
	
	private Neuron inputNeuron;
	private Neuron outputNeuron;
	private float weight;
	private float lastDcoDi;
	
	public  Synapse() {
		this.weight = random.nextFloat();
	}
	
	public float getWeight() {
		return this.weight;
	}
	
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	public void setNeurons(Neuron inputNeuron, Neuron outputNeuron) {
		this.inputNeuron = inputNeuron;
		this.outputNeuron = outputNeuron;
	}
	
	public Neuron getInputNeuron() {
		return this.inputNeuron;
	}
	
	public Neuron getOutputNeuron() {
		return this.outputNeuron;
	}
	
	public float getLastDcoDi() {
		return this.lastDcoDi;
	}
	
}