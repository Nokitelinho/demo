<?php
/*
*
* The template used for single page, for use by the theme
*
*/
?>
/*
* Getting the tenant from the url
*/
<?php
	session_start(); 
	$current_url = $_SERVER['HTTP_REFERER']; 
	if (strpos($current_url, "cmpcod") > 0){
		$cmpcod = substr(strrchr( $current_url, '='), 1);
	}
?>
<ul id="hkb" class="hkb-searchresults" role="listbox">
    	<?php echo $_GET['ht-kb-dummy']; ?>
	<?php $total_results = 0; ?><?php 
	

	?>
	<!-- ht_kb -->
	<?php if ( have_posts() ) : ?>
		<?php $counter = 0; ?>
		<?php $total_results += (int) $wp_query->posts; ?>
	
		<?php
		while ( have_posts() && $counter < 10 ) :
			the_post();
			?>
	      <?php $post_slug = $post->post_name; ?>
	<?php if (strpos($post_slug, $cmpcod) > 0) { ?>
			<li class="hkb-searchresults__article <?php hkb_post_type_class(); ?> <?php hkb_post_format_class(); ?>" role="option">
      // appending the tenant code with the post's url
				<a href="<?php $custom_termlink= the_permalink();
								$custom_termlink .= "?cmpcod=$cmpcod";
								echo $custom_termlink; ?>">
					<span class="hkb-searchresults__title"><?php the_title(); ?></span><?pho echo $v1; ?>
					<?php if ( hkb_show_search_excerpt() && function_exists( 'hkb_the_excerpt' ) && hkb_get_the_excerpt() ) : ?>
						<span class="hkb-searchresults__excerpt"><?php hkb_the_excerpt(); ?></span>
					<?php endif; ?> 
				</a>
			</li> <?php } ?>
			<?php $counter++; ?>
		<?php endwhile; ?>
	<?php endif; ?>

	<?php if ( $total_results > 0 ) : ?>
		<li class="hkb-searchresults__showall" role="option">
			<a href="<?php echo esc_url( apply_filters( 'hkb_search_url', $s ) ); ?>"><?php esc_html_e( 'Show all results', 'knowall' ); ?></a> 
		</li>
	<?php else : ?>
		<li class="hkb-searchresults__noresults" role="option">
			<span><?php esc_html_e( 'No Results', 'knowall' ); ?></span>
		</li>
	<?php endif; ?>
</ul>

<!-- script added to remove the header when postMessage is called -->
<script type="text/javascript">
	var v1 = "nm";
	//jQuery(document).ready(function($) {
	jQuery('.hkb').ready(function (){
		$catergory_article=document.getElementById("category_article").value;
     v1=$catergory_article;
		 console.log(v1);
})

</script>
