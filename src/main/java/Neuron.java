import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Neuron {
	
	public static Random random;
	
	private List<Synapse> inputSynapses;
	private List<Synapse> outputSynapses;
	private float bias;
	private float currentValue;
	private float currentOutput;
	private float dcdw;
	private boolean isInputLayer;
	
	public Neuron() {
		this.inputSynapses = new ArrayList<>();
		this.outputSynapses = new ArrayList<>();
		
		this.bias = random.nextFloat();
		this.currentValue = 0;
		
		this.isInputLayer = false;
	}
	
	public float getBias() {
		return this.bias;
	}
	
	public void setBias(float bias){
		this.bias = bias;
	}
	
	public float getCurrentValue() {
		return this.currentValue;
	}
	
	public void setCurrentValue(float  currentValue) {
		this.currentValue = currentValue;
	}
	
	public void addInputSynapses(List<Synapse> synapses) {
		this.inputSynapses.addAll(synapses);
	}
	
	public void addOutputSynapse(Synapse synapse) {
		this.outputSynapses.add(synapse);
	}
	
	public float getOutput() {
		if (this.isInputLayer)
		    return this.currentValue;
		    
		this.currentValue = 0;
		
		this.inputSynapses.forEach(synapse -> {
			if (synapse.getInputNeuron() != null) {
			this.currentValue += synapse.getInputNeuron().getOutput() * synapse.getWeight();
			
			//System.out.println("   " + synapse.getInputNeuron().getOutput() + "*" + synapse.getWeight());
			}
		});
		
		this.currentValue += this.bias;
		
		this.currentOutput = sigmoid(currentValue);
		
		return this.currentOutput;
	}
	
	public float sigmoid(float input) {
		return (float) (1 / (1 + Math.exp(-input)));
	}
	
	public List<Synapse> getInputSynapses() {
		return this.inputSynapses;
	}
	
	public List<Synapse> getOutputSynapses() {
		return this.outputSynapses;
	}
	
	public void setInputLayer(boolean inputLayer) {
		this.isInputLayer = inputLayer;
	}
	
	public boolean isInputLayer() {
		return this.isInputLayer;
	}
	
	public float getCurrentOutput() {
		return this.currentOutput;
	}
	
	public float getDerivatedOutput() {
		return this.currentOutput * (1 - this.currentOutput);
	}
	
	public void setDcDw(float dcdw) {
		this.dcdw = dcdw;
	}
	
	public float getDcDw() {
		return this.dcdw;
	}
	
}