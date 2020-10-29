package com.animetabase;

public class Season {
		private String name;
		private int episode;
		private int totalEpisodes;
		
		public Season(String name, int episode, int totalEpisodes) {
			this.name = name;
			this.episode = episode;
			this.totalEpisodes = totalEpisodes;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getEpisode() {
			return episode;
		}

		public void setEpisode(int episode) {
			this.episode = episode;
		}

		public int getTotalEpisodes() {
			return totalEpisodes;
		}

		public void setTotalEpisodes(int totalEpisodes) {
			this.totalEpisodes = totalEpisodes;
		}
		
		private void nextEpisode() {
			this.episode ++;
		}
}
