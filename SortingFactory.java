public class SortingFactory {

    /**
     * Gets a SortingAlgorithm instance according to the parameter.
     *
     * The SortingFactory is a modified version of a class by the same name, originally written by David Guy Brizan
     *
     * @param algoName Sorting algorithm name; one of: selection, bubble, insertion, quick, merge.
     * @return An instance of the sorting algorithm.
     * @throws Exception If the name of the algorithm is invalid.
     */
    public SortingAlgorithm getSortingAlgorithm(String algoName) throws Exception {
        String lowercaseAlgoName = algoName.toLowerCase();

        if (lowercaseAlgoName.contains("hybrid")) {
            return new HybridSort();
        }
        if (lowercaseAlgoName.contains("merge")) {
            return new MergeSort();
        }

        throw new Exception("Invalid algorithm name specified");
    }
}
