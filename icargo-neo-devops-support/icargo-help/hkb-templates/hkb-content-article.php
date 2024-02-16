/*
* Getting the tenant from Session
*/
<?php
	session_start();
	$cmpcod = $_SESSION['tenant'];
?>
<article id="post-<?php the_ID(); ?>" class="hkb-articlemini" itemscope itemtype="https://schema.org/CreativeWork">
	// appending the tenant code with the article url
	<a class="hkb-article__link" href="<?php $custom_termlink= the_permalink();
	$custom_termlink .= "?cmpcod=$cmpcod";
	echo $custom_termlink; ?>">
		<?php $tag_list = get_the_term_list( $post->ID, 'ht_kb_tag', '', '', '' ); 
		if (strpos($tag_list, 'ParentTag') > 0) { ?>
			<h2 class="hkb-article__title" itemprop="headline">
					<?php the_title(); ?>	
			</h2>
		<?php } else { ?>
		<h4 class="sub-article-header"><?php the_title(); ?></h4>
		<?php } ?>

	<?php if ( hkb_show_taxonomy_article_excerpt() && hkb_get_the_excerpt() ) : ?>
		<div class="hkb-article__excerpt">
			<?php hkb_the_excerpt(); ?>
		</div>
	<?php endif; ?>
	</a>
</article>