FROM nginx:alpine

# Copy Nginx configuration
COPY nginx.conf /etc/nginx/nginx.conf
COPY site.conf /etc/nginx/conf.d/site.conf

# Delete default files
RUN rm -f /etc/nginx/conf.d/default.conf
RUN rm -rf /usr/share/nginx/html

# Copy website files
RUN mkdir -p /data
COPY html /data/html

# Copy asset files
COPY assets.zip /data/assets.zip
RUN unzip /data/assets.zip -d /data/html

# Setup Nginx
RUN chown -R nginx:nginx /data
