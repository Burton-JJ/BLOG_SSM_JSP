package tech.acodesigner.dao.cache;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import tech.acodesigner.dto.ArticleDto;
import tech.acodesigner.entity.Article;

/**
 *
 * @description
 * @author Burton
 * @date 2018/5/11 15:14
 * @param
 * @return
 *
 */
public class RedisDao {
    private final JedisPool jedisPool;

    private RuntimeSchema<ArticleDto> schema = RuntimeSchema.createFrom(ArticleDto.class);

    public RedisDao(JedisPoolConfig config, String addr) {
        jedisPool = new JedisPool(config, addr);
    }

    public ArticleDto getArticleByIdInCache(int articleId) {
        Jedis jedis = jedisPool.getResource();
        String key = "article" + articleId;

        byte[] bytes = jedis.get(key.getBytes());

        if (bytes != null) {
            ArticleDto article = schema.newMessage();
            ProtostuffIOUtil.mergeFrom(bytes, article, schema);
            jedis.close();
            return article;

        }
        jedis.close();
        return null;

    }

    public String saveArticleInCache(ArticleDto article) {
        Jedis jedis = jedisPool.getResource();
        String key = "article" + article.getArticleId();
        byte[] bytes = ProtostuffIOUtil.toByteArray(article, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        //第二个参数为有效期 时间一到 redis缓存中就没有了
        String result = jedis.setex(key.getBytes(), 60 * 60, bytes);
        jedis.close();
        return result;
    }

//    public String saveArticleInCache1(ArticleDto article) {
//        Jedis jedis = jedisPool.getResource();
//        String key1 = "article1" + article.getArticleId();
//        String key2 = "article12" + article.getArticleId();
//        byte[] bytes = ProtostuffIOUtil.toByteArray(article, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
//        //第二个参数为有效期 时间一到 redis缓存中就没有了
//        String result1 = jedis.setex(key1.getBytes(), 60 * 60, bytes);
//        String result2 = jedis.setex(key2.getBytes(), 60 * 60, bytes);
//        jedis.close();
//        return result1;
//    }

    public void updateCachel(ArticleDto article){
        Jedis jedis = jedisPool.getResource();
        String key = "article" + article.getArticleId();
        String key2 = "article2" + article.getArticleId();
        byte[] bytes = ProtostuffIOUtil.toByteArray(article, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        String result = jedis.setex(key2.getBytes(), 60 * 60, bytes);
        jedis.expire(key, 5);
    }

    public boolean isInCachel(Article article){
        Jedis jedis = jedisPool.getResource();
        String key = "article" + article.getArticleId();

        if(!jedis.exists(key)){
            return false;
        }
        else {
            return true;
        }

    }

    public void setNoUse(int articleId){
        Jedis jedis = jedisPool.getResource();
        String key = "article" +articleId;
        jedis.expire(key, 0);
    }
}
