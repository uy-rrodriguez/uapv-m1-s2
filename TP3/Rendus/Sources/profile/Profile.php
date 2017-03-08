<?php
/**
 * Class to time the execution of method calls.
 * Example usage:
 *   $p = new Profile();
 *   $time = $p->profile("classname", "methodname", array(arg1, arg2, ...));
 *   $p->printDetails();
 *
 * You can also provide an optional number to profile, which will result
 * in the method getting called that many times. Details are then recoded
 * about the total execution time, average time, and worst single time.
 *
 * @author Ben Dowling - www.coderholic.com
 */
class Profile {
	/**
	 * Stores details about the last profiled method
	 */
	private $details;

	public function __construct() {

	}

    /**
     * Fait le calcul de l'écart-type à partir dun array de valeurs float.
     * Retourne un float qui représente l'écart-type ou FALSe en cas d'erreur.
     *
     * https://bytes.com/topic/php/answers/160398-fonction-ecarttype-en-php
     */
    private function ecarttype($array, $nbdecimals=2) {
        if (is_array($array)) {
            //moyenne des valeurs
            reset($array);
            $somme=0;
            $nbelement=count($array);
            foreach ($array as $value) {
                $somme += floatval($value);
            }
            $moyenne = $somme/$nbelement;
            //numerateur
            reset($array);
            $sigma=0;
            foreach ($array as $value) {
                $sigma += pow((floatval($value)-$moyenne),2);
            }
            //denominateur = $nbelement
            $ecarttype = sqrt($sigma/$nbelement);
            return number_format($ecarttype, $nbdecimals);
        }
        else {
            return false;
        }
    }


	/**
	 * Runs a method with the provided arguments, and
	 * returns details about how long it took. Works
	 * with instance methods and static methods.
	 *
	 * @param classname string
	 * @param methodname string
	 * @param methodargs array
	 * @param invocations int The number of times to call the method
	 * @return float average invocation duration in seconds
	 */
	public function profile($classname, $methodname, $methodargs, $invocations = 1) {
		if(class_exists($classname) != TRUE) {
			throw new Exception("{$classname} doesn't exist");
		}

		$method = new ReflectionMethod($classname, $methodname);

		$instance = NULL;
		if(!$method->isStatic()) 		{
			$class = new ReflectionClass($classname);
			$instance = $class->newInstance();
		}

		$durations = array();

        // Utilisation de mémoire
        // Mémoire avant la boucle
        $memoryStart = memory_get_usage();

		for($i = 0; $i < $invocations; $i++) {
			$start = microtime(true);
            $method->invokeArgs($instance, $methodargs);
			$durations[] = microtime(true) - $start;

            /*
            // Différence de mémoire avant et après l'exécution
            $memoryUsages[] = $memoryPeak - $memoryStart;
            $memoryPeaks[] = $memoryPeak;
            */
		}

        // Mémoire maximale après la boucle
        $memoryPeak = memory_get_peak_usage();


        // ----------- Calculs sur la durée -----------
		$duration['total'] = round(array_sum($durations), 4);
		$duration['average'] = round($duration['total'] / count($durations), 4);
		$duration['worst'] = round(max($durations), 4);

        // Ecart-type
        $duration['standard_deviation'] = $this->ecarttype($durations, 6);


        // ----------- Calculs sur la mémoire -----------
        $memory['start'] = round($memoryStart, 4);
        $memory['peak'] = round($memoryPeak, 4);
        $memory['difference'] = round($memoryPeak - $memoryStart, 4);

        /*
        $memory['total'] = round(array_sum($memoryUsages), 4);
		$memory['average'] = round($memory['total'] / count($memoryUsages), 4);
		$memory['worst'] = round(max($memoryUsages), 4);

		$peaksTotal = round(array_sum($memoryPeaks), 4);
		$memory['average_peak'] = round($peaksTotal / count($memoryPeaks), 4);

        // Ecart-type
        $memory['standard_deviation'] = $this->ecarttype($memoryUsages, 6);
        */



		$this->details = array(	'class' => $classname,
							   	'method' => $methodname,
							   	'arguments' => $methodargs,
						 		'duration' => $duration,
								'invocations' => $invocations,
						 		'memory' => $memory);

		return $duration['average'];
	}

	/**
	 * Returns a string representing the last invoked
	 * method, including any arguments
	 * @return string
	 */
	private function invokedMethod() {
		return "{$this->details['class']}::{$this->details['method']}(" .
			 join(", ", $this->details['arguments']) . ")";
	}

	/**
	 * Prints out details about the last profiled method
	 */
	public function printDetails() {
		$methodString = $this->invokedMethod();
		$numInvoked = $this->details['invocations'];

		if($numInvoked == 1) {
			echo "{$methodString} took {$this->details['duration']['average']}s\n";
		}
		else {
			echo "{$methodString} was invoked {$numInvoked} times\n";
			echo "Total duration:   {$this->details['duration']['total']}s\n";
			echo "Average duration: {$this->details['duration']['average']}s\n";
			echo "Worst duration:   {$this->details['duration']['worst']}s\n";
            echo "Standard deviation:   {$this->details['duration']['standard_deviation']}s\n";

            echo "\nMEMORY:\n";
			echo "Memory on start:   {$this->details['memory']['start']} b\n";
			echo "Memory peak:       {$this->details['memory']['peak']} b\n";
			echo "Memory difference: {$this->details['memory']['difference']} b\n";

            /*
			echo "Average memory: {$this->details['memory']['average']}\n";
            echo "Average memory peak:   {$this->details['memory']['average_peak']}\n";
			echo "Worst memory:   {$this->details['memory']['worst']}\n";
            echo "Standard deviation memory:   {$this->details['memory']['standard_deviation']}\n";
            */

            echo "\n";
		}
	}
}

?>
