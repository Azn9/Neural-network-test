import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {

	public static void main(String[] args) {

		//Use the same global Random instance. Useful for seeding
		Random random = new Random(0);

		Neuron.random = random;
		Synapse.random = random;

		//Building the network

		List<Neuron> inputLayer = new ArrayList<>();
		List<List<Neuron>> hiddenLayers = new ArrayList<>();
		List<Neuron> outputLayer = new ArrayList<>();

		for (int i = 0; i < 2; i++) {
			Neuron neuron = new Neuron();
			neuron.setInputLayer(true);

			inputLayer.add(neuron);
		}

		for (int layer = 0; layer < 1; layer++) {
			List<Neuron> layerNeurons = new ArrayList<>();

			for (int i = 0; i < 2; i++) {
				Neuron neuron = new Neuron();

				layerNeurons.add(neuron);

				if (layer > 0) {
					List<Synapse> inputSynapses = new ArrayList<>();

					hiddenLayers.get(layer - 1).forEach(hiddenNeuron -> {
						Synapse synapse = new Synapse();

						synapse.setNeurons(hiddenNeuron, neuron);

						hiddenNeuron.addOutputSynapse(synapse);

						inputSynapses.add(synapse);
					});

					neuron.addInputSynapses(inputSynapses);
				}
			}

			hiddenLayers.add(layerNeurons);
		}

		System.out.println(hiddenLayers.get(0).size());

		hiddenLayers.get(0).forEach(neuron -> {
			List<Synapse> inputSynapses = new ArrayList<>();

			inputLayer.forEach(hiddenNeuron -> {
				Synapse synapse = new Synapse();

				synapse.setNeurons(hiddenNeuron, neuron);

				System.out.println("a");

				hiddenNeuron.addOutputSynapse(synapse);

				inputSynapses.add(synapse);
			});

			neuron.addInputSynapses(inputSynapses);
		});

		for (int i = 0; i < 2; i++) {
			Neuron neuron = new Neuron();

			outputLayer.add(neuron);

			List<Synapse> inputSynapses = new ArrayList<>();

			hiddenLayers.get(hiddenLayers.size() - 1).forEach(hiddenNeuron -> {
				Synapse synapse = new Synapse();

				synapse.setNeurons(hiddenNeuron, neuron);

				hiddenNeuron.addOutputSynapse(synapse);

				inputSynapses.add(synapse);
			});

			neuron.addInputSynapses(inputSynapses);
		}

		//Network built

		inputLayer.forEach(hiddenNeuron -> {
			System.out.println(hiddenNeuron.getOutputSynapses().size());
		});


		//Manual parametrization (tests)
		inputLayer.get(0).setCurrentValue(2);
		inputLayer.get(1).setCurrentValue(3);

		hiddenLayers.get(0).get(0).setBias(0.25f);
		hiddenLayers.get(0).get(1).setBias(0.45f);
		outputLayer.get(0).setBias(0.15f);
		outputLayer.get(1).setBias(0.35f);

		hiddenLayers.get(0).get(0).getInputSynapses().get(0).setWeight(0.3f);
		hiddenLayers.get(0).get(0).getInputSynapses().get(1).setWeight(-0.4f);
		hiddenLayers.get(0).get(1).getInputSynapses().get(0).setWeight(0.2f);
		hiddenLayers.get(0).get(1).getInputSynapses().get(1).setWeight(0.6f);

		outputLayer.get(0).getInputSynapses().get(0).setWeight(0.7f);
		outputLayer.get(0).getInputSynapses().get(1).setWeight(0.5f);
		outputLayer.get(1).getInputSynapses().get(0).setWeight(-0.3f);
		outputLayer.get(1).getInputSynapses().get(1).setWeight(-0.1f);

		List<Float> output = Arrays.asList(
								 outputLayer.get(0).getOutput(),
								 outputLayer.get(1).getOutput()
							 );

		List<Float> expected = Arrays.asList(
								   1f,
								   0.2f
							   );

		System.out.println("Y1: " + output.get(0));
		System.out.println("Y2: " + output.get(1));

		float cost = 0;

		for (int i = 0; i < output.size(); i++) {
			cost += Math.pow(expected.get(i) - output.get(i), 2);
		}

		System.out.println("Cost: " + cost);
		
		System.out.println("===| Backpropagation |===");
		
		
		System.out.println("OW : " + outputLayer.get(0).getInputSynapses().get(0).getWeight());
		
		float learningRate = 0.1f;
		

		for (int i = 0; i < outputLayer.size(); i++) {
			System.out.println("N " + i);
			
			Neuron neuron = outputLayer.get(i);
			float didw = neuron.getCurrentOutput();
			
			System.out.println(didw);

			float dodi = neuron.getDerivatedOutput();
			
			System.out.println(dodi);

			float dcdo = 2 * (neuron.getCurrentOutput() - expected.get(i));
			
			System.out.println(dcdo);

			float dcdw = didw * dodi * dcdo;
			
			System.out.println("New dcdw : " + dcdw);

			neuron.setDcDw(dcdw);

			if (!neuron.isInputLayer())
				for (int j = 0; j < neuron.getInputSynapses().size(); j++) {
					System.out.println("  S " + i);
					
					
					Synapse synapse = neuron.getInputSynapses().get(j);
					
					float currentWeight = synapse.getWeight();
					
					dcdo = synapse.getInputNeuron().getCurrentOutput();
					
					dcdw = dcdo * dodi * didw;
					
					System.out.println(dcdw);
								
					synapse.setWeight(currentWeight - learningRate * dcdw);
				}
		}
		
		System.out.println("NW : " + outputLayer.get(0).getInputSynapses().get(0).getWeight());
	}
}