//package com.java8.streamTutorial;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Stream is a sequence of objects/primitives from a source which supports aggregation operations.
 *
 ** Sequence of elements: a stream computes an element on demand (lazily)
 ** Source: set, list, map, array, files, SQL
 ** Aggregate operations: map, filter, reduce, collect, match, find
 ** Pipelining: Stream operations return streams, so operations could be pipelined.
 ** Intermediate Operations: takes an input element, processes it and returns output to the target stream
 ** Terminal Operations: Marks the end of stream pipelining. Streams get evaluated only when
 *                an terminal operation is encountered.
 ** Automatic iteration: Streams internally iterate over the source elements. No external iteration is required.
 *
 *
 */
class Tutorial{

	public static String PATH_SONGS = "/Users/ameya/Documents/StreamsPlayGround/StreamsTutorial/resources/songs.csv";
	public static String PATH_TAGS  = "/Users/ameya/Documents/StreamsPlayGround/StreamsTutorial/resources/tags.csv";

	public static List<Songs> songs = getSongs();
	public static List<Tags> tags = getTags();

	public static void main(String ar[]) throws IOException {

	}


	/**
	 * Stream for a range of numbers
	 * @param n
	 * @return
	 */
	public static long sumOfNats(int n){
		return IntStream.range(0,n).sum();
	}

	public static IntSummaryStatistics summaryOfNats(int n){
		return IntStream.range(0,n).summaryStatistics();
	}

	/**
	 * Stream of Strings from a list
	 * 'joining' is a terminal operation
	 * @return joins them with " "
	 */
	public static String streamExample1(){
		final List<String> l = Arrays.asList("Hi!", "how", "are", "you");
		return l.stream().collect(Collectors.joining(" "));
	}

	/**
	 *
	 * @param ns
	 * 'filter' is an intermediate operation
	 * @return even numbers in the list
	 */
	public static List<Integer> getEvenNumbers(List<Integer> ns){
		return ns.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
	}

	/**
	 * 'map' is an intermediate operation
	 * @return the Tags from the csv dataset
	 */
	public static List<Tags> getTags(){
		return read(PATH_TAGS)
				.stream()
				.filter(x -> x.length == 4)
				// map String array to objects of Tags
				.map(x -> new Tags(x[0],x[1],x[2],x[3]))
				.collect(Collectors.toList());
	}


	/**
	 *
	 * @return the songs from the csv dataset
	 */
	public static List<Songs> getSongs(){
		return read(PATH_SONGS)
				.stream()// stream creation
				.filter(x -> x.length == 5)
				// map String array to objects of Songs
				.map(x -> new Songs(x[0],x[1],x[2],x[3],x[4]))
				.collect(Collectors.toList());
	}

	/**
	 *
	 * @param path of the file
	 * @return converts each line to string array, returns list of string arrays
	 */
	public static List<String[]> read(String path){
	    try {
	    	// create stream out of each line in the file
            return Files.lines(Paths.get(path))
                    .skip(1)
                    .map(x -> x.split(";"))
                    .collect(Collectors.toList());
        }catch(Exception e){
            System.out.println("File not found!!!");
        }
	    return new ArrayList<>();
    }

	/**
	 * Captures the songs information
	 */
    public static class Songs {
		private String id;
		private String name;
		private String artist;
		private String position;
		private String genre;

		Songs(String id, String name, String artist, String position, String genre) {
			this.id = id;
			this.name = name;
			this.artist = artist;
			this.position = position;
			this.genre = genre;
		}

		public String getId() { return id; }

		public String getName() { return name; }

		public String getArtist() { return artist; }

		public String getPosition() { return position; }

		public String getGenre() { return genre; }
	}


	/**
	 * Captures tags information
	 */
	public static class Tags{

		private String id;
		private String genre;
		private String tag;
		private String popularity;

		public Tags(String id, String genre, String tag, String popularity) {
			this.id = id;
			this.genre = genre;
			this.tag = tag;
			this.popularity = popularity;
		}

		public String getId() { return id; }

		public String getGenre() { return genre;}

		public String getTag() { return tag; }

		public String getPopularity() { return popularity; }
	}

}